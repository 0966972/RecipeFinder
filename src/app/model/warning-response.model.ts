import {User} from "./user.model";

export class WarningResponse {
    public warnedUser: User;
    public message: string;
}
