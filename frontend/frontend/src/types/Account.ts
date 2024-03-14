// for retrieving account
export interface Account {
    id: string;
    userId: string;
    account_name: string;
    account_username: string;
    account_password: string;
}

// display at password vault
export interface displayAccount {
    account_name: string;
    account_username: string;
    account_password: string;
}

// for editing account
export interface AccountResult {
    id: string;
    accountname: string;
    username: string;
    password: string;
}