import {fetchSavePost} from "./fetchAddPost.js";

const searchPatternURL = new RegExp('/add-post');
if (window.location.pathname.match(searchPatternURL)) {
    const BLOG_POST_ENDPOINT = '/api/posts/';
    document.getElementById("submit")
        .addEventListener('click', function (e) {
            e.preventDefault();
            fetchSavePost.save(BLOG_POST_ENDPOINT);
        });
}

