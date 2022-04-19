function showNotification(text) {
    const notification = document.querySelector('.mdl-js-snackbar');
    notification.MaterialSnackbar.showSnackbar(
        {
            message: text
        }
    );
}

$(function () {
    let clientDialog = document.getElementById("museum_dialog");
    if (!clientDialog.showModal) {
        dialogPolyfill.registerDialog(clientDialog);
    }
});

$(function () {
    $("#add-museum").click(function () {

        let dialog = document.getElementById("museum_dialog");
        $("#museum_dialog_form")[0].reset();
        $("#add_museum_confirm").show();
        $("#edit_museum_confirm").hide('fast');
        dialog.showModal();
    });

    $("#close").click(function (){
        let dialog = document.getElementById("user_dialog");
        dialog.close();
    });
});


function editMuseum(ctl) {
    console.log("Inside editMuseum(ctl)")
    let id = $(ctl).parents("tr").children()[0].innerHTML;
    let name = $(ctl).parents("tr").children()[2].innerHTML;
    let address = $(ctl).parents("tr").children()[3].innerHTML;
    let tel = $(ctl).parents("tr").children()[4].innerHTML;
    let city = $(ctl).parents("tr").children()[5].innerHTML;
    let country = $(ctl).parents("tr").children()[6].innerHTML;
    let type = $(ctl).parents("tr").children()[8].innerHTML;

    console.log("evo ga id: " + id);

    // let elements = document.getElementById("user_dialog_form").elements;
    let $form = $("#museum_dialog_form");
    $form[0].reset();

    $('#name').val(name);
    $('#name').parent().addClass('is-dirty')
    $('#address').val(address);
    $('#address').parent().addClass('is-dirty')
    $('#tel').val(tel);
    $('#tel').parent().addClass('is-dirty')
    $('#city').val(city);
    $('#city').parent().addClass('is-dirty')
    $('#country').val(country);
    $('#country').parent().addClass('is-dirty');
    $('#type').val(type);
    $('#type').parent().addClass('is-dirty');

    let dialog = document.getElementById("museum_dialog");

    $form.append('<input type="hidden" name="id" id="id"/>');
    $('#id').attr("value", id);
    $("#edit_museum_confirm").show();
    $("#add_museum_confirm").hide('fast');
    dialog.showModal();
}
//needed to disable row click event when user clicks Edit button
$(function (){
    $("#museums_table tr").not('.edit-button').on("click",function(){
       const museumId = $(this).children()[0].innerHTML;
        location.href = "visits.jsp?museum_id=" + museumId;
    });
});


// Initialize and add the map
function initMap() {
    // The location of Uluru
    const uluru = { lat: -25.344, lng: 131.036 };
    // The map, centered at Uluru
    const map = new google.maps.Map(document.getElementById("map"), {
        zoom: 4,
        center: uluru,
    });
    // The marker, positioned at Uluru
    const marker = new google.maps.Marker({
        position: uluru,
        map: map,
    });
}

