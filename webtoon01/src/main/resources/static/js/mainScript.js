// section1 script
// 날짜 함수
function section1_today() {
    let today = new Date();
    let day = today.getDay();
    let todayArr = ["일", "월", "화", "수", "목", "금", "토"];
    document.querySelector("h1").innerText = todayArr[day] + "요웹툰";
    document.querySelector("#section1_today_category a").innerHTML = todayArr[day] + "요웹툰 전체보기 ";

}
// 캐로셀 함수
function section1_desktop_script() {
    const section1_slide_prev = document.querySelector("#section1_slide_prev");
    const section1_slide_next = document.querySelector("#section1_slide_next");
    const section1_slide = document.querySelector(".section1_slide_move");
    const section1_category = document.querySelectorAll(".section1_btn_wrap button");
    let slide_count = 0;
    section1_slide.style.left = slide_count + "px";
    // 캐로셀 prev 함수
    section1_slide_prev.onclick = function () {

        section1_slide_next.style.display = "block"
        slide_count += 1204;
        section1_slide.style.left = slide_count + "px";
        if (slide_count == 0) {
            section1_slide_prev.style.display = "none";
        }
    }
    // 캐로셀 next 함수
    section1_slide_next.onclick = function () {
        section1_slide_prev.style.display = "block"
        slide_count -= 1204;
        section1_slide.style.left = slide_count + "px";
        if (slide_count == -3612) {
            section1_slide_next.style.display = "none";
        }
    }
    // 카테고리 버튼 색
    for (let i = 0; i < section1_category.length; i++) {
        section1_category[i].onclick = function () {
            for (let j = 0; j < section1_category.length; j++) {
                section1_category[j].style.color = "#4e4e4e";
            }
            section1_category[i].style.color = "#00dc64";
            section1_slide.style.left = 0;
            slide_count = 0;
            section1_slide_prev.style.display = "none";
            section1_slide_next.style.display = "block"
        };
    }
}
// /section1 script

// mobile js
function section1_mobile_script() {
    const container = document.querySelector(".section1_slide_wrap");
    const wrapper = document.querySelector(".section1_slide_move");
    let card_count = document.querySelectorAll(".section1_card").length - 1;
    let currentIndex = 0;
    let startX = null;
    wrapper.style.left = "0px"
    function goToSlide(index) {
        // .section1_slide_move의 left 값을 조정
        wrapper.style.left = `-${index * 360}px`;
    }

    function handleStart(event) {
        const touch = event.type === 'touchstart' ? event.touches[0] : event;
        startX = touch.clientX;
    }

    function handleMove(event) {
        if (!startX) return;

        const touch = event.type === 'touchmove' ? event.touches[0] : event;
        const diff = touch.clientX - startX;

        // 슬라이드 이동에 따라 .section1_slide_move의 left 값을 조정
        wrapper.style.left = `calc(-${currentIndex * 360}px + ${diff}px)`;
    }

    function handleEnd(event) {
        if (startX === null) return;

        const touch = event.type === 'touchend' ? event.changedTouches[0] : event;
        const diff = touch.clientX - startX;

        if (diff > 50 && currentIndex > 0) {
            currentIndex--;
        } else if (diff < -50 && currentIndex < card_count) {
            currentIndex++;
        }

        // 슬라이드 이동
        goToSlide(currentIndex);
        startX = null;
    }


    container.addEventListener('touchstart', handleStart);
    container.addEventListener('touchmove', handleMove);
    container.addEventListener('touchend', handleEnd);

    // 초기 슬라이드 위치 설정
    goToSlide(currentIndex);
}

function section1_script() {
    section1_today();

    if (window.matchMedia("(min-width: 768px)").matches) {
        section1_desktop_script();
    } else if (window.matchMedia("(max-width: 767px)").matches) {
        section1_mobile_script();
    }
}
// /section1

// section2 script
function section2_genre() {
    const buttons = document.querySelectorAll(".section2_btndv button");
    const buttonstext = document.querySelectorAll(".tabButton");


    for (let i = 0; i < buttons.length; i++) {
        buttons[i].onclick = function () {
            for (let j = 0; j < buttons.length; j++) {
                buttons[j].style.color = "#4e4e4e";
            }
            buttons[i].style.color = "#00dc64";
            slide_count = 0;
        }
    };
    // 버튼 클릭 시 텍스트 변경
    // 모든 버튼 요소들을 가져옵니다.

    // 각 버튼에 대해 클릭 이벤트를 설정합니다.
    buttonstext.forEach((button) => {
        button.addEventListener("click", () => {
            // 모든 버튼에 있는 "active" 클래스를 제거합니다.
            buttonstext.forEach((btn) => {
                btn.classList.remove("active");
            });

            // 클릭된 버튼에 "active" 클래스를 추가하여 텍스트 색상을 변경합니다.
            button.classList.add("active");

            // 클릭된 버튼의 텍스트를 가져와서 h3와 .linkText 요소를 업데이트합니다.
            const buttonText = button.querySelector("span").textContent;
            const h3TextElement = document.querySelector(".section2_titdv .h3Text");
            const linkTextElement = document.querySelector(".section2_titdv .linkText");

            h3TextElement.textContent = buttonText;
            linkTextElement.textContent = buttonText;
        });
    });

    // 초기에 첫 번째 버튼에 "active" 클래스를 추가하여 텍스트 색상을 초록색으로 설정합니다.
    buttonstext[0].classList.add("active");
    buttons[0].classList.add("active");
}
// section2_rank script
function showContent(type) {
    const lists = document.querySelectorAll('.section2_hs_list li');
    const buttons = document.querySelectorAll('.section2_hs_btn button');
    lists.forEach(item => item.classList.remove('active'));
    buttons.forEach(btn => btn.classList.remove('active'));
    if (type === 'all') {
        lists.forEach(item => item.classList.add('active'));
    } else if (type === 'male') {
        document.querySelectorAll('.section2_hs_list li:not(.section2_hs_list li.section2_hs_list)').forEach(item => item.classList.add('active'));
    } else if (type === 'female') {
        document.querySelectorAll('.section2_hs_list li:not(.section2_hs_list li.section2_hs_list)').forEach(item => item.classList.add('active'));
    }
    // 버튼 텍스트에 색상 변경 클래스 적용
    buttons.forEach(btn => {
        if (btn.getAttribute('onclick') === `showContent('${type}')`) {
            btn.classList.add('active');
        } else {
            btn.classList.remove('active');
        }
    });
}

function section2_script() {
    section2_genre();
    showContent('all');
}

// 모바일 태블릿 화면 전환 함수
let bWidth = window.innerWidth;
let size = 1;
window.addEventListener("resize", () => {
    const nWidth = window.innerWidth;
    if (((bWidth >= 768 && nWidth < 768) || (bWidth < 768 && nWidth >= 768)) && size==1) {
        size = 0;
        setTimeout(() => {
            location.reload();
        }, 500);

    } else if (bWidth < 768 && nWidth >= 768){
        size = 1;
    }

});

document.addEventListener('DOMContentLoaded', function () {
    section1_script();
    section2_script();

});