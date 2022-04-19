var BATTUTA_KEY = "577f7c1d1c4927c948cf8fd4670a9358";

$(document).ready(function () {

    $.ajax({
        type: "GET",
        url: "https://restcountries.com/v3.1/region/europe",
        dataType: 'json',
        success: function (result) {
            result.forEach(country => $('#country').append(" <option value=" + country.cca2 + ">" + country.name.common + "</option>"))
        }
    });
});

$(function () { /* DOM ready */
    $("#country").change(function () {
        let battuta_url = "http://battuta.medunes.net/api/region/" + $(this).val() + "/all/?key=" + BATTUTA_KEY + "&callback=?"
        console.log(battuta_url);

        $("#city option").remove();

        $.ajax({
            type: "GET",
            url: battuta_url,
            dataType: 'json',
            success: function (result) {
                if(result.length)
                    result.forEach(region =>  getCitiesByRegion(region));
                else{
                    alert("nema regiona");
                }
            }
        });

    });
});

function getCitiesByRegion(region){
    console.log('The region with value ' + region.region);
    let battuta_url = url = "http://battuta.medunes.net/api/city/" + region.country +"/search/?region=" + region.region + "&key=" + BATTUTA_KEY+ "&callback=?";
    $.ajax({
        type: "GET",
        url: battuta_url,
        dataType: 'json',
        success: function (result) {
            result.forEach(city => {
                $('#city').append(" <option value=" + city.city + ">" + city.city + "</option>")
            });
        }
    });
}