$(function(){
     setTimeout(function() {
             window.scrollTo(0, 500);
                  }, 500);
                  });
$(window).on('scroll', function() {
    var windowHeight = $(window).height();
    var scrollTop = $(window).scrollTop();

    $('#sec1').each(function() {
      var sectionTop1 = $(this).offset().top;
      if (sectionTop1 < scrollTop + windowHeight - 100) {
        $(this).addClass('active');
      }
    });

    $('#sec2').each(function() {
      var sectionTop2 = $(this).offset().top;
      if (sectionTop2 < scrollTop + windowHeight - 200) {
        $(this).addClass('active');
      }
    });

    $('#sec3').each(function() {
      var sectionTop3 = $(this).offset().top;
      if (sectionTop3 < scrollTop + windowHeight - 200) {
        $(this).addClass('active');
      }
    });

    $('#sec4').each(function() {
          var sectionTop3 = $(this).offset().top;
          if (sectionTop3 < scrollTop + windowHeight - 200) {
            $(this).addClass('active');
          }
        });
  });



$(document).ready(function() {
  $('.col-lg-3').hover(function() {
    $(this).find('h5, button').css('opacity', '1');
  }, function() {
    $(this).find('h5, button').css('opacity', '0');
  });

});