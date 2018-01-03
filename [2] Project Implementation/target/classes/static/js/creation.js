//Preview gambar yang diupload di item
$(document).on('click', '#close-preview', function(){
    $('.image-preview').popover('hide');
    // Hover befor close the preview
    $('.image-preview').hover(
        function () {
            $('.image-preview').popover('show');
        },
        function () {
            $('.image-preview').popover('hide');
        }
    );
});

$(function() {
    // Create the close button
    var closebtn = $('<button/>', {
        type:"button",
        text: 'x',
        id: 'close-preview',
        style: 'font-size: initial;',
    });
    closebtn.attr("class","close pull-right");
    // Set the popover default content
    $('.image-preview').popover({
        trigger:'manual',
        html:true,
        title: "<strong>Preview</strong>"+$(closebtn)[0].outerHTML,
        content: "There's no image",
        placement:'bottom'
    });
    // Clear event
    $('.image-preview-clear').click(function(){
        $('.image-preview').attr("data-content","").popover('hide');
        $('.image-preview-filename').val("");
        $('.image-preview-clear').hide();
        $('.image-preview-input input:file').val("");
        $(".image-preview-input-title").text("Browse");
    });
    // Create the preview image
    $(".image-preview-input input:file").change(function (){
        var img = $('<img/>', {
            id: 'dynamic',
            width:250,
            height:200
        });
        var file = this.files[0];
        var reader = new FileReader();
        // Set preview image into the popover data-content
        reader.onload = function (e) {
            $(".image-preview-input-title").text("Change");
            $(".image-preview-clear").show();
            $(".image-preview-filename").val(file.name);
            img.attr('src', e.target.result);
            $(".image-preview").attr("data-content",$(img)[0].outerHTML).popover("show");
        }
        reader.readAsDataURL(file);
    });
});

function inputDisabled() {
    var payment = document.getElementById("payment").value;
    var receiptButton = document.getElementById("receiptKartu");
    var bayarButton = document.getElementById("bayar");

    if (payment == "") {
        document.getElementById("nominal").disabled = true;
        document.getElementById("nominal").value = "";
        bayarButton.style.display = "block";
        bayarButton.disabled = true;
        receiptButton.disabled = true;
        receiptButton.style.display = "none";
    }
    if (payment == "Kartu Kredit/Debit") {
        document.getElementById("nominal").disabled = true;
        document.getElementById("nominal").value = "";
        receiptButton.disabled = false;
        receiptButton.style.display = "block";
        bayarButton.style.display = "none";
    }
    if (payment == "Tunai") {
        document.getElementById("nominal").disabled = false;
        receiptButton.style.display = "none";
        bayarButton.style.display = "block";
        bayarButton.disabled = false;
    }
}

// MULTIPLE MODAL
//set button id on click to hide first modal
$("#bayar").on( "click", function() {
    $('#cartModal').modal('hide');
});
//trigger next modal
$("#bayar").on( "click", function() {
    $('#changeModal').modal('show');
});

function hitungKembalian() {

    var total = document.getElementById("totalModal").innerHTML;
    var nominal = document.getElementById("nominal").value;
    var bayarButton = document.getElementById("bayar");

    var kembalian = nominal - total;

    $("#kembalian").text(kembalian);

    //Cek jika jumlah pembayaran masih kurang dari total belanja
    if (nominal < total) {
        bayarButton.disabled = true;
    }
    else {
        bayarButton.disabled = false;
    }

}

//DISCOUNT
function addDiscount() {

    var discountId = document.getElementById("diskon");
    var diskon = discountId.options[discountId.selectedIndex].value;
    var disc1 = document.getElementById("disc1");
    var disc2 = document.getElementById("disc2");

    $.getJSON('http://localhost:8080/cashier/discount/' + diskon, function (data) {
        $("#discountPercentage").text(data.disc);

        countTotal(data.disc);

    });

    disc1.value = diskon;
    disc2.value = diskon;
}

function countTotal(percentage) {

    var subtotal = document.getElementById("subtotal");

    var total = subtotal.innerHTML * (100-percentage)/100;

    $("#total").text(total);
    $("#totalModal").text(total);
    
}

function getCustName() {

    var customer = document.getElementById("customer");
    var cust1 = document.getElementById("cust1");
    var cust2 = document.getElementById("cust2");

    cust1.value = customer.value;
    cust2.value = customer.value;

}



