export const fetchBlogPostCards = (function () {
    const POST_LIST_ENDPOINT = `/api/posts`;

    async function fetchAllPosts() {
        try {
            const response = await fetch(POST_LIST_ENDPOINT, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if (!response.ok)
                throw new Error(`Failed to fetch posts: ${response.status}`);

            return await response.json();
        } catch (e) {
            alert("An Error Occurred Check Console Log !");
            console.log(e);
        }
    }

    function displayPosts() {
        fetchAllPosts()
            .then((postsList) => {
                populateRow(postsList);
            });
    }

    function populateRow(postsList) {
        if (!postsList) {
            console.log("No Entry !");
            return;
        }

        let container = document.getElementById("row-container");
        let row = document.createElement("div")
        row.classList.add("row");
        container.append(row);

        let count = 0;
        for (const post of postsList) {
            if (count == 2) {
                row = document.createElement("div");
                row.classList.add("row");
                container.append(row);

                count = 0;
            }
            let column = buildColumn(post.imagePath, post.title,
                post.authorName, `blog-post?id=${post.blogId}`);
            row.append(column);

            count++;
        }
    }

    function buildColumn(imageSrc, title, authorName, anchorLink) {
        //Creating elements
        let div_col = document.createElement("div");
        div_col.classList.add("col");

        let div_card = document.createElement("div");
        div_card.classList.add("card");

        let div_cardBody = document.createElement('div');
        div_cardBody.classList.add("card-body");

        let imageTag = document.createElement("img");
        imageTag.classList.add("card-img-top");

        let h5Tag = document.createElement("h5");
        h5Tag.classList.add("card-title");
        h5Tag.innerText = title;

        let paragraphTag = document.createElement("p");
        paragraphTag.classList.add("card-text");
        paragraphTag.innerText = `Author: ${authorName}`;

        let anchorTag = document.createElement("a");
        anchorTag.classList.add("btn");
        anchorTag.classList.add("btn-primary");
        anchorTag.innerText = "Go To Post";

        //Populating element attributes
        div_card.style.width = "18rem";
        imageTag.setAttribute("th:src", `@{${imageSrc}}`);
        imageTag.setAttribute("src", imageSrc);
        imageTag.setAttribute("height", "200px");

        anchorTag.setAttribute("th:href", `@{${anchorLink}}`);
        anchorTag.setAttribute("href", anchorLink);

        //Structure DOM
        div_cardBody.append(h5Tag);
        div_cardBody.append(paragraphTag);
        div_cardBody.append(anchorTag);

        div_card.append(imageTag);
        div_card.append(div_cardBody);

        div_col.appendChild(div_card);

        return div_col;
    }


    return {
        displayAllPostCards: displayPosts,
        populateRow: populateRow
    }
})();