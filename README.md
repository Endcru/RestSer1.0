Автор: Константинов Никита Дмитриевич
Проект: Ресторан из микросервисов

Система работы:

Проект поделён на 4 части
ClientService - клиенская часть приложения, где можно регестрироваться, авторизироваться и работать с заказами
Server-data - Часть сервера, отвечающего за базу данных
Server-rest- Часть сервера отвечающего за запроссы от клиента, принимающая их и передающая указания в business
Server-business - Часть сервера отвечающая за бизнесс логику

Клиент-серверное взаимодействие основанно на JWT

База данных создана с помощью Постресса:

create table "user" (
                      id serial primary key,
                      username varchar(50) unique not null,
                      email varchar(100) unique not null,
                      password_hash varchar(225) not null,
                      role varchar(10) not null check (role in ('customer', 'chef', 'manager')),
                      created_at timestamp default current_timestamp,
                      updated_at timestamp default current_timestamp
);

create table "session" (
                        id serial primary key,
                        user_id int not null ,
                        session_token varchar(255) not null,
                        expires_at timestamp not null ,
                        foreign key (user_id)
                        references "user"(id)
);

CREATE TABLE dish (
                      id serial primary key,
                      name varchar(100) not null ,
                      description text,
                      price decimal(10, 2) not null ,
                      quantity int not null ,
                      is_available boolean not null ,
                      created_at timestamp default current_timestamp,
                      updated_at timestamp default current_timestamp
);


create table "order"
(
                        id serial primary key,
                        user_id int not null ,
                        status varchar(50) not null,
                        special_requests text,
                        created_at timestamp default current_timestamp,
                        updated_at timestamp default current_timestamp,
                        foreign key (user_id)
                        references "user"(id)
);

CREATE TABLE order_dish (
                            id serial primary key,
                            order_id int not null,
                            dish_id int not null,
                            quantity int not null,
                            price decimal(10, 2) not null ,
                            foreign key (order_id)
                            references "order"(id),
                            foreign key (dish_id)
                            references "dish"(id)
);

CREATE  FUNCTION update_updated_on_user_task()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = now();
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_user_task_updated_on
    BEFORE UPDATE
    ON
        "user"
    FOR EACH ROW
EXECUTE PROCEDURE update_updated_on_user_task();

CREATE TRIGGER update_user_task_updated_on
    BEFORE UPDATE
    ON
        "order"
    FOR EACH ROW
EXECUTE PROCEDURE update_updated_on_user_task();

CREATE TRIGGER update_user_task_updated_on
    BEFORE UPDATE
    ON
        "dish"
    FOR EACH ROW
EXECUTE PROCEDURE update_updated_on_user_task();
