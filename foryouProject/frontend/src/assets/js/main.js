// 배너
const buttons = document.querySelectorAll("[data-carousel-button]");

buttons.forEach(button => {
    button.addEventListener("click", () => {
        console.log("00");
        const offset = button.dataset.carouselButton === "next" ? 1 : -1
        const slides = button
            .closest("[data-carousel]")
            .querySelector("[data-slides]")
        const activeSlide = slides.querySelector("[data-active]")
        let newIndex = [...slides.children].indexOf(activeSlide) + offset

        // 첫번째에서 마지막 페이지 이동
        if (newIndex < 0) newIndex = slides.children.length - 1

        // 마지막에서 첫번째 페이지 이동
        if (newIndex >= slides.children.length) newIndex = 0

        console.log(newIndex);

        slides.children[newIndex].dataset.active = true
        delete activeSlide.dataset.active
    })
})


// 헤더 고정
const header = document.querySelector(".header");
window.addEventListener('scroll', function () {
    let isButtom = false;
    let currentScroll = document.querySelector('html').scrollTop,
        htmlHeight = document.querySelector('html').scrollHeight,
        currentHeight = document.querySelector('html').clientHeight;

    if (window.pageYOffset >= 130) { // window.pageYOffset
        header.classList.add('header-fixed');
    } else {
        header.classList.remove('header-fixed');
    }

    if (!isButtom) {
        if (currentScroll + currentHeight >= htmlHeight) {
            isButtom = true;
            console.log("끝입니다.");
        }
    }
})



// 사이드 카테고리
const category = document.querySelectorAll(".category-content li"),
    categoryA = document.querySelectorAll(".category-content a");

category.forEach((c, index) => {
    c.addEventListener("mouseover", function (e) {
        categoryA[index].style.color = 'white';
    })

    c.addEventListener("mouseleave", function (e) {
        categoryA[index].style.color = 'black';
    })
})


let categoryBtn = document.querySelector('.category-panel'),
    categoryWrap = document.querySelector(".category"),
    categoryhidden = document.querySelector(".category-hidden"),
    body = document.querySelector('body'),
    ishide = true;

categoryBtn.addEventListener("click", function () {
    if (ishide) {
        categoryhidden.classList.add('category-visible');
        categoryWrap.style.transform = "translateX(0)";
        ishide = false;
    }
})

body.addEventListener("click", function (e) {
    if (e.target.dataset.closeCategory === '') {
        categoryWrap.style.transform = "translateX(-350px)";
        categoryhidden.classList.remove('category-visible');
        ishide = true;
    }
})



// // 상품 카드
// const card = document.querySelector('.card-panel');
// const likeBtn = document.querySelector('.like-btn-panel');

// const drawStar = (target) => {
//     document.querySelector('.star span').style.width = `${target.value * 10}%`;
// }

// card.addEventListener('mouseover', function() {
//     likeBtn.classList.remove('fade-out');
//     likeBtn.classList.add('fade-in');
// });

// card.addEventListener('mouseout', function() {
//     likeBtn.classList.remove('fade-in');
//     likeBtn.classList.add('fade-out');
// });
// // 상품 카드 끝