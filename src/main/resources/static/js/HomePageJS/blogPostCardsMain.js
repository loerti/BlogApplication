import {fetchBlogPostCards} from "./fetchBlogPostCards.js";

const homePatternURL = new RegExp('/home-page');
if (window.location.pathname.match(homePatternURL))
    fetchBlogPostCards.displayAllPostCards();

