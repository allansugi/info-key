import { Account, AccountResult } from "./Account";

export interface ResponseGetAccount {
    success: Boolean,
    response: Account[]
}