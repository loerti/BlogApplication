import {fetchBlogPostCards} from "../HomePageJS/fetchBlogPostCards.js";

export let fetchAdvancedFilter = (function () {

    async function fetchAdvancedFilter(url) {
        try {
            const response = await fetch(url, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if (!response.ok)
                throw new Error(`Failed to apply filter: ${response.status}`);

            return await response.json();
        } catch (e) {
            alert("An Error Occurred Check Console Log !");
            console.log(e);
        }
    }

    function displayFilterResult(url) {
        fetchAdvancedFilter(url)
            .then((postsList) => {
                if (postsList.length == 0)
                    document.getElementById("noResult-label").style.display = 'block';
                else
                    fetchBlogPostCards.populateRow(postsList);
            });
    }

    return {
        displayFilterResult: displayFilterResult
    }
})();