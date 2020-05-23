'use strict';

const serverPath = 'http://localhost:8080/api';

const getCurrentPage = () => {
    const loc = (typeof window.location !== 'string') ? window.location.search : window.location;
    const index = loc.indexOf('page=');
    return index === -1 ? 1 : parseInt(loc[index + 5]) + 1;
};

const constructGetUrl = (url, queryParams) => {
    for (let key in queryParams) {
        if (queryParams.hasOwnProperty(key)) {
            console.log(key, queryParams[key]);
        }
    }
};

(function loadCategoriesPageable() {
    const categoryTemplate = (item) => {
        const template = `<div class="card">
                 <div class="card">
            <div class="box-128">

                <p>${item.date}</p>
                <p>${item.text}</p>
            </div>
        </div>
            </div>`;

        const elem = document.createElement('div');
        elem.innerHTML = template.trim();
        return elem.children[0];
    };                      //making div element for new category

    const fetchCategories = async (page, size) => {
        const categoriesPath = `${serverPath}/comments?page=${page}&size=${size}`;
        const data = await fetch(categoriesPath, {cache: 'no-cache'});
        return data.json();
    };           //getting categories in json format using page and size

    const loadNextCategoriesGenerator = (loadNextElement, page) => {
        return async (event) => {
            event.preventDefault();
            event.stopPropagation();

            const defaultPageSize = loadNextElement.getAttribute('data-default-page-size');
            const data = await fetchCategories(page, defaultPageSize);

            if(data.length === 0){
                document.getElementById("loadNextIcons").classList.add("display_none");
                return;
            }

            const categories = document.getElementById('categoryList');
            for (let category of data) {
                categories.append(categoryTemplate(category));
            }

            loadNextElement.addEventListener('click', loadNextCategoriesGenerator(loadNextElement, page + 1), {once: true});
            window.scrollTo(0, document.body.scrollHeight);
        };
    };     //loading categories from next page

    document.getElementById('loadPrevIcons').classList.add("display_none");              //making load prev hidden

    const loadNextElement = document.getElementById('loadNext');    //renaming loadNext to loadMore and adding function
    if (loadNextElement !== null) {
        loadNextElement.addEventListener('click', loadNextCategoriesGenerator(loadNextElement, getCurrentPage()), {once: true});
    }
})();