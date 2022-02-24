export let fetchBlogPost = (function () {

    async function fetchBlogPost(url) {
        try {
            const response = await fetch(url, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if (!response.ok)
            {
                let data = await response.json();
                console.log(`Error Details:
                HTTP STATUS: ${data.httpStatus}
                Cause: ${data.message}`
                );
                throw new Error(data.message);
            }

            return await response.json();
        } catch (e) {
            alert(e);
            window.history.go(-1);
        }
    }

    function displayBlogPost(url) {
        const container = document.getElementById("post-container");
        fetchBlogPost(url)
            .then((post) => {
                const postHTML = generatePostHTML(post.title, post.authorName,
                    post.dateStamp, post.imagePath, post.content, post.blogCategoryModel.categoryName);
                container.innerHTML = (postHTML);
            });
    }

    function generatePostHTML(title, author, date, image, description, categoryName) {
        const postHTML = `
                <h1>
                    <b>${title}</b> 
                </h1>
                <h2>
                    <b>Created By: <i>${author}</i> </b>                   
                </h2>

                <h3 id="date-header">
                    <b>Posted On:</b>
                    <i>${date}</i>
                    <br>
                    <b>Category:</b> 
                    <i>${categoryName}</i> 
                </h3>

                <img th:src="@{${image}}" src="${image}" width="700px"/>

                <p id="blogPost-description">
                    ${description}
                </p>

                <div id="btn-container" class="d-grid gap-2 d-md-block">
                    <a th:href="@{edit-post?id=${postId}}" href="edit-post?id=${postId}"> 
                        <buttton type="button" class="btn btn-outline-primary">Edit</buttton> 
                    </a>
                    <buttton type="button" class="btn btn-outline-danger"
                        data-bs-toggle="modal" data-bs-target="#delete-modal">Delete</buttton>
                </div>`

        return postHTML;
    }

    return {
        displayBlogPost: displayBlogPost,
    }


})();