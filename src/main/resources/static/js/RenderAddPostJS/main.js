import {renderAddPostTemplate} from "./renderAddPostTemplate.js";

const editPatternURL = new RegExp('/edit-post');
if (window.location.pathname.match(editPatternURL)) {
    const BLOG_POST_ENDPOINT = `/api/posts/${postId}`;
    renderAddPostTemplate.displayTemplate(BLOG_POST_ENDPOINT);
}

