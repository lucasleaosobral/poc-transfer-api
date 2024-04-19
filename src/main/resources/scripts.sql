

CREATE TABLE IF NOT EXISTS wallets (
       id UUID PRIMARY KEY,
       balance NUMERIC(10,2) DEFAULT 0 not null
);

CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) not null,
    email VARCHAR(100) UNIQUE not null,
    document VARCHAR(16) UNIQUE not null,
    password VARCHAR(100) not null,
    account_type text not null,
    wallet_id UUID UNIQUE not null,
    FOREIGN KEY (wallet_id) REFERENCES wallets(id)
);
