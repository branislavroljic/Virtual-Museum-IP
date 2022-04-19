
$(function () {
    let clientDialog = document.getElementById("visit_dialog");
    if (!clientDialog.showModal) {
        dialogPolyfill.registerDialog(clientDialog);
    }
    // let artifactDialog = document.getElementById("artifact_dialog");
    // if (!clientDialog.showModal) {
    //     dialogPolyfill.registerDialog(clientDialog);
    // }
});

$(function () {
    $("#add-visit").click(function () {

        let dialog = document.getElementById("visit_dialog");
        // resetForm();
        //   document.getElementById("client_root_dir").removeAttribute("readonly");
        document.getElementById("visit_dialog_form").reset();
        $("#add_visit_confirm").show();
        $("#edit_visit_confirm").hide('fast');
        //$("#").css("display", "block");
        dialog.showModal();
    });

    $("#close").click(function (){
        let dialog = document.getElementById("user_dialog");
        dialog.close();
    });
});

function addSubmit(){
    let images = document.getElementById("images");
    let video = document.getElementById("video");
    let ytvideo = document.forms["visit_dialog_form"]["ytvideo"];
    let ytlink = ytvideo.value;
    console.log(video.files.length);
    if(images.files.length < 5 || images.files.length > 10){
        alert("You must upload 5-10 images!");
        return false;
    }else if(video.files.length === 0 && (ytlink == null || ytlink ==="")){
        alert("You must upload a video or provide YT link");
        return false;
    }else if(video.files.length > 0 && ytlink != ''){
        alert("You must upload a video OR provide YT link");
        return false;
    }else if(video.files.length === 0){
        video.setAttribute("disabled", "disabled");
    }else if(ytlink == null || ytlink == ''){
        ytvideo.setAttribute("disabled", "disabled");
    }
    return true;
}

// function showArtifacts(ctl){
//     let id = $(ctl).parents("tr").children()[0].innerHTML;
// }



// function editMuseum(ctl) {
//     console.log("Inside editVisit(ctl)")
//     let id = $(ctl).parents("tr").children()[0].innerHTML;
//     let name = $(ctl).parents("tr").children()[2].innerHTML;
//     let address = $(ctl).parents("tr").children()[3].innerHTML;
//     let tel = $(ctl).parents("tr").children()[4].innerHTML;
//     let city = $(ctl).parents("tr").children()[5].innerHTML;
//     let country = $(ctl).parents("tr").children()[6].innerHTML;
//     let type = $(ctl).parents("tr").children()[8].innerHTML;
//
//     console.log("evo ga id: " + id);
//
//     // let elements = document.getElementById("user_dialog_form").elements;
//     let form = document.getElementById("museum_dialog_form");
//     form.reset();
//
//     $('#name').val(name);
//     $('#name').parent().addClass('is-dirty')
//     $('#address').val(address);
//     $('#address').parent().addClass('is-dirty')
//     $('#tel').val(tel);
//     $('#tel').parent().addClass('is-dirty')
//     $('#city').val(city);
//     $('#city').parent().addClass('is-dirty')
//     $('#country').val(country);
//     $('#country').parent().addClass('is-dirty');
//     $('#type').val(type);
//     $('#type').parent().addClass('is-dirty');
//
//     let dialog = document.getElementById("museum_dialog");
//
//     $("#edit_museum_confirm").off('click').on('click', function () {
//         // form.validate(); //ne radi, nmp zasto
//         // if (!form.valid())
//         //     return;
//
//         $('#museum_dialog_form').append('<input type="hidden" name="id" id="id"/>');
//         $('#id').attr("value", id);
//         $('#museum_dialog_form').append('<input type="hidden" name="edit_museum" />'); //TODO moze li ovo bolje?
//         $('#museum_dialog_form').submit();
//         dialog.close();
//     });
//     $("#edit_museum_confirm").show();
//     $("#add_museum_confirm").hide('fast');
//
//     dialog.showModal();
// }
