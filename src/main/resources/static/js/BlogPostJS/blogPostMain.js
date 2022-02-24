import {fetchBlogPost} from "./fetchBlogPost.js";
import {deleteBlogPost} from "./deleteBlogPost.js";

const blogPostPatternURL = new RegExp('/blog-post');
if (window.location.pathname.match(blogPostPatternURL)) {
    const BLOG_POST_ENDPOINT = `/api/posts/${postId}`;
    fetchBlogPost.displayBlogPost(BLOG_POST_ENDPOINT);

    document.getElementById("btn-deleteConfirmation")
        .addEventListener('click', function () {
            deleteBlogPost.delete(BLOG_POST_ENDPOINT);
        });
}


