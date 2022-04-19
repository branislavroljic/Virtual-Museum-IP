<%-- Client input dialog --%>
<dialog id="museum_dialog" class="mdl-dialog">
    <div class="mdl-dialog__title">Museum</div>
    <form id="museum_dialog_form" action="museums.jsp" method="POST">
        <div class="mdl-dialog__content">
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                <input class="mdl-textfield__input" type="text" name="name" id="name" required autofocus>
                <label class="mdl-textfield__label" for="name">Name</label>
            </div>
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                <input class="mdl-textfield__input" type="text" name="address" id="address" required>
                <label class="mdl-textfield__label" for="address">Address</label>
            </div>
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                <input class="mdl-textfield__input" type="text" name="tel" id="tel" required>
                <label class="mdl-textfield__label" for="tel">Phone</label>
            </div>
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                <input class="mdl-textfield__input" type="text" name="geolat" id="geolat" required>
                <label class="mdl-textfield__label" for="geolat">GeoLat</label>
            </div>
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                <input class="mdl-textfield__input" type="text" name="geolng" id="geolng" required>
                <label class="mdl-textfield__label" for="geolng">GeoLng</label>
            </div>
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                <input class="mdl-textfield__input" type="text" name="type" id="type" required>
                <label class="mdl-textfield__label" for="type">Type</label>
            </div>

            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                <select class="mdl-textfield__input" id="country" name="country" required>

                </select>
                <label class="mdl-textfield__label" for="country">Country</label>
            </div>

            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                <select class="mdl-textfield__input" id="city" name="city" required>

                </select>
                <label class="mdl-textfield__label" for="city">City</label>
            </div>
        </div>
        <div class="mdl-dialog__actions">
            <button type="button" class="mdl-button close" id="close">Close</button>
            <button type="submit" name="add_museum" id="add_museum_confirm" class="mdl-button">Save</button>
            <button type="submit" name="edit_museum" id="edit_museum_confirm" class="mdl-button">Edit</button>
        </div>
    </form>
</dialog>
