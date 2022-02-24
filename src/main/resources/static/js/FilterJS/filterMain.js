import {fetchSearchFilter} from "./fetchSearchFilter.js";
import {fetchAdvancedFilter} from "./fetchAdvancedFilter.js";


const searchPatternURL = new RegExp('/search');
if (window.location.pathname.match(searchPatternURL)) {
    const SEARCH_ENDPOINT = `api/search?keyword=${search_keyword}`;
    fetchSearchFilter.displaySearchResult(SEARCH_ENDPOINT);
}

const filterPatternURL = new RegExp('/filter');
if (window.location.pathname.match(filterPatternURL)) {
    const ADVANCED_FILTER_ENDPOINT = `api/filter?category=${filter_category === null ? "" : filter_category}`
        + `&dateOrder=${filter_date === null ? "" : filter_date}`;
    fetchAdvancedFilter.displayFilterResult(ADVANCED_FILTER_ENDPOINT);
}
