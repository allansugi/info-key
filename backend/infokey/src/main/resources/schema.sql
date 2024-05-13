DROP TABLE IF EXISTS user_account;
DROP TABLE IF EXISTS vault_account;

CREATE TABLE IF NOT EXISTS user_account (
    id varchar(36) NOT NULL,
    username text,
    email text,
    password text,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS vault_account (
    id varchar(36) NOT NULL,
    userId varchar(36),
    account_name text,
    account_username text,
    account_password text,
    PRIMARY KEY (id),
    FOREIGN KEY (userId) REFERENCES user_account(id) ON DELETE CASCADE
);