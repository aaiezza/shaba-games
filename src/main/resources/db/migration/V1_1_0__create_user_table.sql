---------------------
-- user table
---------------------

CREATE TABLE IF NOT EXISTS "user" (
      "user_id" SERIAL NOT NULL PRIMARY KEY
    , "email" VARCHAR (100) NOT NULL
    , "username" VARCHAR (50) NOT NULL
    , "password" VARCHAR (130)
    , "first_name" VARCHAR (45)
    , "last_name" VARCHAR (45)
    , "created_at" TIMESTAMPTZ (3) NOT NULL DEFAULT CURRENT_TIMESTAMP
    , "updated_at" TIMESTAMPTZ (3) NOT NULL DEFAULT CURRENT_TIMESTAMP
    , UNIQUE(email)
    , UNIQUE(username)
)
;

ALTER TABLE "user"
    ALTER created_at
        TYPE TIMESTAMPTZ(3) USING created_at AT TIME ZONE 'UTC'
;
ALTER TABLE "user"
    ALTER updated_at
        TYPE TIMESTAMPTZ(3) USING updated_at AT TIME ZONE 'UTC'
;

INSERT INTO "user" (email, username, password, first_name, last_name) VALUES
    ( 'admin@example.com'
    , 'admin'
    , '$2a$10$i79PCEIXSC9NZGiF7v1NUOyhceg9nocY8D.0rYMT4M3pdzam55noa' -- "admin"
    , 'Admin'
    , 'Admin'
    )
;

---------------------
-- role table
---------------------

CREATE TABLE IF NOT EXISTS "role" (
      "role_id" SERIAL NOT NULL PRIMARY KEY
    , "name" varchar(50)
    , "created_at" TIMESTAMPTZ (3) NOT NULL DEFAULT CURRENT_TIMESTAMP
)
;

ALTER TABLE "role"
    ALTER created_at
        TYPE TIMESTAMPTZ(3) USING created_at AT TIME ZONE 'UTC'
;

INSERT INTO "role" (name) VALUES
    ('ROLE_USER')
  , ('ROLE_ADMIN')
;

---------------------
-- user_role table
---------------------

CREATE TABLE IF NOT EXISTS "user_role" (
      "user_id" SERIAL NOT NULL
    , "role_id" SERIAL NOT NULL
    , "created_at" TIMESTAMPTZ (3) NOT NULL DEFAULT CURRENT_TIMESTAMP
    , PRIMARY KEY(user_id, role_id)
    , constraint fk_user_roles_user
        foreign key (user_id)
        REFERENCES "user" (user_id)
    , constraint fk_user_roles_role
        foreign key (role_id)
        REFERENCES "role" (role_id)
)
;

ALTER TABLE "user_role"
    ALTER created_at
        TYPE TIMESTAMPTZ(3) USING created_at AT TIME ZONE 'UTC'
;

INSERT INTO "user_role" (user_id, role_id) VALUES
      ( 1, 1 )
    , ( 1, 2 )
;
