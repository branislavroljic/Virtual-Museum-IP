function  reloadTransactions(){
    console.log("refresh=click");
    $.ajax({
        type: "GET",
        url: "?action=reload_transactions",
        cache: false,
        dataType :'json',
        success: function (response) {
            let tbody = document.getElementById("transaction_body");

            tbody.innerHTML = "";
            for(let i in response){

                console.log("repsonse je: "  + JSON.stringify(response[i]));
                let tr = document.createElement("tr");
                let td1 = document.createElement("td");
                let td2 = document.createElement("td");
                let td3 = document.createElement("td");
                td1.setAttribute("class", "mdl-data-table__cell--non-numeric");
                td2.setAttribute("class", "mdl-data-table__cell--non-numeric");
                td1.innerHTML = response[i].date;
                td2.innerHTML = response[i].description;
                td3.style.textAlign = "right";
                td3.innerHTML = response[i].amount.toFixed(2);
                tr.append(td1);
                tr.append(td2);
                tr.append(td3);
                tbody.append(tr);
            }
        },
        error : function (){
            alert("An error occurred while reloading transactions!");
        }
    });
}