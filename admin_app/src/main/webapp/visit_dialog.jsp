<%-- Client input dialog --%>
<dialog id="visit_dialog" class="mdl-dialog">
    <div class="mdl-dialog__title">Visit</div>
    <form id="visit_dialog_form" action="visits.jsp" method="POST" enctype="multipart/form-data">
        <div class="mdl-dialog__content">
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label mdl-textfield--floating-label has-placeholder">
                <input class="mdl-textfield__input" type="date" name="date" id="date" required autofocus>
                <label class="mdl-textfield__label" for="date">Date</label>
            </div>
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label mdl-textfield--floating-label has-placeholder">
                <input class="mdl-textfield__input" type="time" name="time" id="time" required>
                <label class="mdl-textfield__label" for="time">Time</label>
            </div>
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                <input class="mdl-textfield__input" type="number" name="duration" id="duration" required>
                <label class="mdl-textfield__label" for="duration">Duration(min)</label>
            </div>
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                <input class="mdl-textfield__input" type="number" min="1" step="any" name="price" id="price" required>
                <label class="mdl-textfield__label" for="price">Price</label>
            </div>
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label mdl-textfield--floating-label has-placeholder">
                <input class="mdl-textfield__input" type="file" name="images" id="images" multiple="multiple" accept="image/*" required>
                <label class="mdl-textfield__label" for="images">Images</label>
            </div>
            <hr>
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label mdl-textfield--floating-label has-placeholder">
                <input class="mdl-textfield__input" type="file" name="video" id="video" accept="video/mp4">
                <label class="mdl-textfield__label" for="price">Video</label>
            </div>
            <div>
                OR
            </div>
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                <input class="mdl-textfield__input" type="text" name="ytvideo" id="ytvideo">
                <label class="mdl-textfield__label" for="ytvideo">YT link</label>
            </div>
        </div>
        <div class="mdl-dialog__actions">
            <button type="button" class="mdl-button close" id="close">Close</button>
            <button type="submit"  id="add_visit_confirm" class="mdl-button" onclick="return addSubmit()">Save</button>
            <button type="button"  id="edit_visit_confirm" class="mdl-button">Edit</button>
        </div>
    </form>
</dialog>
