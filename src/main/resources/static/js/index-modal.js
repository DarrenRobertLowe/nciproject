/* INDEX MODAL */
// Get the modal
var modal = document.getElementById("product-lightbox");

// Get the image and insert it inside the modal - use its "alt" text as a caption
var img = document.getElementById("index-image");

//var modalContent = document.getElementById("modal-content");


// SHOW MODAL
img.onclick = function(){
    modal.style.display = "block";
    //modalImg.src = this.src;
    //captionText.innerHTML = this.alt;
};


// CLOSE MODAL
// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
    modal.style.display = "none";
};