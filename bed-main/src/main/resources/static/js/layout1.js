$(window).scroll(function(){
            var sct  = $(window).scrollTop();

            if(sct >=200){
                $('nav').removeClass('navbar-transparent');
                $('nav').addClass('bg-light');
            }else{
               $('nav').removeClass('bg-light');
               $('nav').addClass('navbar-transparent');
            }



            });