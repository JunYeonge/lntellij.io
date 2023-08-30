$(window).scroll(function(){
            var sct  = $(window).scrollTop();
            $('#sTop').text(sct);
            if(sct >=200){
                $('nav').removeClass('navbar-transparent');
                $('nav').addClass('bg-light');
            }else{
               $('nav').removeClass('bg-light');
               $('nav').addClass('navbar-transparent');
            }

var swiper1 = new Swiper(".mySwiper1", {
      spaceBetween: 30,
      centeredSlides: true,
//      loop: true,
      autoplay: {
        delay: 15000,
        disableOnInteraction: false,
      },
      navigation: {
        nextEl: ".swiper-button-next",
        prevEl: ".swiper-button-prev"
      }
    });

var swiper2 = new Swiper(".mySwiper2", {
      scrollbar: {
        el: ".swiper-scrollbar",
        hide: false,
      },
    });
var swiper3 = new Swiper(".mySwiper3", {
      slidesPerView: 3,
      spaceBetween: 30,
      pagination: {
        el: ".swiper-pagination",
        clickable: true,
      },
    });

});

