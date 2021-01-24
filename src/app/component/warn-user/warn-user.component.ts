import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {Subscription} from "rxjs";
import {WarningRequest} from "../../model/warning-request.model";
import {AuthService} from "../../service/auth/auth.service";
import {WarningService} from "../../service/warning/warning.service";

@Component({
    selector: 'warn-user',
    templateUrl: './warn-user.component.html',
    styleUrls: ['./warn-user.component.css']
})

export class WarnUserComponent implements OnInit {
    warning: WarningRequest;

    private routeSub: Subscription;

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private http: HttpClient,
        private authService: AuthService,
        private warningService: WarningService,
    ) {
        this.warning = new WarningRequest()
    }

    get isLoggedIn(): boolean {
        return this.authService.authenticated;
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.warning.warnedUserId = params['id'];
        });
    }

    submitWarning() {
        if (this.validateInput()) {
            this.warnUser();
        }
    }

    validateInput(): boolean {
        if (this.isInvalidString(this.warning.message)) {
            alert("Vul een bericht in.")
            return false;
        }
        return true;
    }

    isInvalidString(string: string) {
        return !string || !string.trim() || string == '';
    }

    warnUser() {
        this.warningService.warnUser(this.warning).subscribe(() => {
            this.navigate();
        }, error => {
            alert("Er ging iets mis, probeer het later nog eens.")
        });
    }

    navigate() {
        let previousRoute = this.route.snapshot.paramMap.get('previous');
        this.router.navigate([previousRoute ?? '']);
    }
}
