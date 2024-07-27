create table users
(
    id       int auto_increment
        primary key,
    username varchar(255) not null,
    password varchar(255) not null,
    email    varchar(255) not null,
    constraint unique_email
        unique (email),
    constraint unique_username
        unique (username)
);

create table budgets
(
    id           int auto_increment
        primary key,
    user_id      int            not null,
    name         varchar(100)   null,
    total_amount decimal(10, 2) null,
    start_date   date           null,
    end_date     date           null,
    constraint budgets_ibfk_1
        foreign key (user_id) references users (id)
);

create index user_id
    on budgets (user_id);

create table categories
(
    id          int auto_increment
        primary key,
    user_id     int          not null,
    name        varchar(100) null,
    description varchar(255) null,
    constraint categories_ibfk_1
        foreign key (user_id) references users (id)
);

create table budget_items
(
    id               int auto_increment
        primary key,
    budget_id        int            not null,
    category_id      int            not null,
    allocated_amount decimal(10, 2) null,
    constraint budget_items_ibfk_1
        foreign key (budget_id) references budgets (id),
    constraint budget_items_ibfk_2
        foreign key (category_id) references categories (id)
);

create index budget_id
    on budget_items (budget_id);

create index category_id
    on budget_items (category_id);

create index user_id
    on categories (user_id);

create table reports
(
    id            int auto_increment
        primary key,
    user_id       int          not null,
    name          varchar(100) null,
    creation_date date         null,
    start_date    date         null,
    end_date      date         null,
    constraint reports_ibfk_1
        foreign key (user_id) references users (id)
);

create index user_id
    on reports (user_id);

create table transactions
(
    id          int auto_increment
        primary key,
    user_id     int            not null,
    category_id int            not null,
    amount      decimal(38, 2) null,
    date        date           null,
    description varchar(255)   null,
    constraint transactions_ibfk_1
        foreign key (user_id) references users (id),
    constraint transactions_ibfk_2
        foreign key (category_id) references categories (id)
);

create table report_entries
(
    id             int auto_increment
        primary key,
    report_id      int            not null,
    category_id    int            not null,
    amount         decimal(38, 2) null,
    description    varchar(255)   null,
    transaction_id int            null,
    constraint fk_transaction
        foreign key (transaction_id) references transactions (id),
    constraint report_entries_ibfk_1
        foreign key (report_id) references reports (id),
    constraint report_entries_ibfk_2
        foreign key (category_id) references categories (id)
);

create index category_id
    on report_entries (category_id);

create index report_id
    on report_entries (report_id);

create index category_id
    on transactions (category_id);

create index user_id
    on transactions (user_id);

create table user_profiles
(
    profile_id   int auto_increment
        primary key,
    user_id      int          not null,
    first_name   varchar(50)  null,
    last_name    varchar(50)  null,
    address      varchar(255) null,
    phone_number varchar(20)  null,
    constraint user_profiles_ibfk_1
        foreign key (user_id) references users (id)
);

create index user_id
    on user_profiles (user_id);

create table user_reports
(
    user_id   int not null,
    report_id int not null,
    primary key (user_id, report_id),
    constraint user_reports_ibfk_1
        foreign key (user_id) references users (id),
    constraint user_reports_ibfk_2
        foreign key (report_id) references reports (id)
            on delete cascade
);

