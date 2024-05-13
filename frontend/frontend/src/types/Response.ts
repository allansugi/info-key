import { AccountViewModel } from "./Account";

export interface ResponseGetAccount {
    success: Boolean,
    response: AccountViewModel[]
}