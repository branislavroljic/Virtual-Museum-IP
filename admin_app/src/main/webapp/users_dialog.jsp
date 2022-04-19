<%-- Client input dialog --%>
<dialog id="user_dialog" class="mdl-dialog">
    <div class="mdl-dialog__title">User</div>
    <form id="user_dialog_form" action="users.jsp" method="POST">
        <div class="mdl-dialog__content">
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                <input class="mdl-textfield__input" type="text" name="username" id="username" required="required"
                       pattern="^[^@#/]{12,}$" autofocus />
                <label class="mdl-textfield__label" for="username">Username</label>
            </div>
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                <input class="mdl-textfield__input" type="password" name="password" id="password"
                       pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{15,64}$" required="required"/>
                <label class="mdl-textfield__label" for="password">Password</label>
            </div>
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                <input class="mdl-textfield__input" type="email" name="email" id="email" required="required" />
                <label class="mdl-textfield__label" for="email">E-mail</label>
            </div>
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                <input class="mdl-textfield__input" type="text" name="name" id="name" required />
                <label class="mdl-textfield__label" for="name">Name</label>
            </div>
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                <input class="mdl-textfield__input" type="text" name="surname" id="surname" required />
                <label class="mdl-textfield__label" for="surname">Surname</label>
            </div>
            <div>
                <label>Role</label>
                <label class="mdl-radio mdl-js-radio mdl-js-ripple-effect" for="option-1">
                    <input type="radio" id="option-1" class="mdl-radio__button" name="role" value="USER" checked>
                    <span class="mdl-radio__label">User</span>
                </label>
                <label class="mdl-radio mdl-js-radio mdl-js-ripple-effect" for="option-2">
                    <input type="radio" id="option-2" class="mdl-radio__button" name="role" value="ADMIN">
                    <span class="mdl-radio__label">Admin</span>
                </label>
            </div>
        </div>
        <div class="mdl-dialog__actions">
            <button type="button" class="mdl-button close" id="close">Close</button>
            <button type="submit" name="add_user" id="add_user_confirm" class="mdl-button">Save</button>
            <button type="submit" name="edit_user" id="edit_user_confirm" class="mdl-button">Save</button>
        </div>
    </form>
</dialog>
