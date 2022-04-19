
function showNotification(text){
    const notification = document.querySelector('.mdl-js-snackbar');
    notification.MaterialSnackbar.showSnackbar(
        {
            message: text
        }
    );
}

$(function () {
    let clientDialog = document.getElementById("user_dialog");
    if (!clientDialog.showModal) {
        dialogPolyfill.registerDialog(clientDialog);
    }
});

$(function () {
    $("#add-client").click(function () {
        let dialog = document.getElementById("user_dialog");
        let $form = $('#user_dialog_form');
        $form[0].reset();
        $form.append('<input type="hidden" name="status" value="ACTIVE"/>')
        $("#add_user_confirm").show();
        $("#edit_user_confirm").hide('fast');
        //$("#").css("display", "block");
        dialog.showModal();
    });

    $("#close").click(function (){
        let dialog = document.getElementById("user_dialog");
        dialog.close();
    });
});

// $(function () {
//     $("#add_user_confirm").click(function () {
//         //TODO iz nekog razloga nece da prihvati name="add_user" iz forme kao parametar, isto je i sa edit_user
//        let $userDialogForm = $('#user_dialog_form').append('<input type="hidden" name="add_user" />');
//         $userDialogForm.append('<input type="hidden" name="status" value="ACTIVE"/>');
//         if($userDialogForm[0].checkValidity()){
//             $userDialogForm.submit();
//             $('#user_dialog').close();
//         }
//         else  alert("nije validaaa");
//     });
// })


function editClient(ctl){
    console.log("Inside editClient(ctl)")
    let id = $(ctl).parents("tr").children()[0].innerHTML;
    let username = $(ctl).parents("tr").children()[2].innerHTML;
    let name = $(ctl).parents("tr").children()[3].innerHTML;
    let surname = $(ctl).parents("tr").children()[4].innerHTML;
    let mail = $(ctl).parents("tr").children()[5].innerHTML;


    let $form = $("#user_dialog_form");
    $form[0].reset();

    $('#username').val(username);
    $('#username').parent().addClass('is-dirty')
    $('#name').val(name);
    $('#name').parent().addClass('is-dirty')
    $('#surname').val(surname);
    $('#surname').parent().addClass('is-dirty')
    $('#email').val(mail);
    $('#email').parent().addClass('is-dirty')

        $form.append('<input type="hidden" name="id" id="id"/>');
        $('#id').attr("value", id);

    let dialog = document.getElementById("user_dialog");

    // $("#edit_user_confirm").off('click').on('click', function () {
    //     // form.validate(); //ne radi, nmp zasto
    //     // if (!form.valid())
    //     //     return;
    //
    //     $('#user_dialog_form').append('<input type="hidden" name="id" id="id"/>');
    //     $('#id').attr("value", id);
    //     $('#user_dialog_form').append('<input type="hidden" name="edit_user" />'); //TODO moze li ovo bolje?
    //     $('#user_dialog_form').submit();
    //     dialog.close();
    // });
    $("#edit_user_confirm").show();
    $("#add_user_confirm").hide('fast');

    dialog.showModal();
}

// function handleActiveDisabled(ctl){
//     console.log("Pozvan switch");
//     console.log($(ctl).is('.is-checked'));
//
//     if($(ctl).is('.is-checked')) {
//         $(ctl).parents("form").submit(function(eventObj) {
//             $(this).append('<input type="hidden" name="accept" /> ');
//             return true;
//         });
//     }
//     else {
//         $(ctl).parents("form").submit(function(eventObj) {
//             $(this).append('<input type="hidden" name="block" /> ');
//             return true;
//         });
//     }
//     $(ctl).parents("form").submit();
// }
//
// function toggleStatus(id, ctl){
//     console.log("evo id ja "  + id);
//     $(ctl).parents("form").submit();
// }