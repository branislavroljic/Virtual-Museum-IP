function toggle() {
    let x = document.getElementById("password");
    if (x.type === "password") {
        x.type = "text";
    } else {
        x.type = "password";
    }
}

// $(function (){
//    $('#enable-account').hide('fast');
//    $('#blockedHeader').hide('fast');
// });

$(function (){
    $.ajax({
        type: "GET",
        url: "?action=is_blocked_account",
        cache: false,
        success: function (response) {
            console.log(response);
           if(response == "true") {
               $('#block-account').hide('fast');
           }
           else {
               $('#enable-account').hide('fast');
               $('#blockedHeader').hide('fast');
           }
        },
        error : function (){
            alert("An error occurred while performing action!");
        }
    });
});

$(function () {
    $("#block-account").click(function () {
       blockAccount();
    });
});

$(function () {
    $("#enable-account").click(function () {
        enableAccount();
    });
});

function blockAccount(){
    $.ajax({
        type: "GET",
        url: "?action=change_account_status",
        cache: false,
        success: function () {
            $('#blockedHeader').show(1000);
            $('#enable-account').show(1000);
            $("#block-account").hide('fast');
        },
        error : function (){
            alert("An error occurred while performing action!");
        }
    });
}

function enableAccount(){
    $.ajax({
        type: "GET",
        url: "?action=change_account_status",
        cache: false,
        success: function () {
            $('#blockedHeader').hide('fast');
            $('#block-account').show(1000);
            $("#enable-account").hide('fast');
        },
        error : function (){
            alert("An error occurred while performing action!");
        }
    });
}

// function  reloadTransactions(){
//     $.ajax({
//         type: "GET",
//         url: "?action=reload_transactions",
//         cache: false,
//         dataType :'json',
//         success: function (response) {
//             let tbody = document.getElementById("transaction_body");
//             tbody.innerHTML = "";
//             for(let i in response){
//                 let tr = document.createElement("tr");
//                 let td1 = document.createElement("td");
//                 let td2 = document.createElement("td");
//                 let td3 = document.createElement("td");
//                 td1.setAttribute("class", "mdl-data-table__cell--non-numeric");
//                 td2.setAttribute("class", "mdl-data-table__cell--non-numeric");
//                 td3.setAttribute("class", "mdl-data-table__cell--non-numeric");
//                 td1.innerHTML = response[i].date;
//                 td2.innerHTML = response[i].description;
//                 td3.innerHTML = response[i].amount;
//                 tr.append(td1);
//                 tr.append(td2);
//                 tr.append(td3);
//                 tbody.append(tr);
//             }
//         },
//         error : function (){
//             alert("An error occurred while reloading transactions!");
//         }
//     });
// }