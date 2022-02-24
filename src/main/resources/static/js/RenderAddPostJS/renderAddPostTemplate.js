import {fetchUpdatePost} from "./fetchUpdatePost.js";

export let renderAddPostTemplate = (function () {

    async function fetchBlogPost(url) {
        try {
            const response = await fetch(url, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if (!response.ok)
                throw new Error(`Failed to fetch post: ${response.status}`);

            return await response.json();
        } catch (e) {
            alert("An Error Occurred Check Console Log !");
            console.log(e);
        }
    }

    function generatePostHTML(title, author, description) {
        const HTML =
            `<h1 class="underline">Add Your Post Today !</h1>
    
            <form id="addPost-form" action="">
                <div class="mb-3">
                    <label for="title" class="form-label">Title</label>
                    <input type="text" class="form-control" id="title" value="${title}" placeholder="${title}" required>
                    <div id="titleHelp" class="form-text">Enter the title of the post</div>
                </div>
                <div class="mb-3">
                    <label for="context" class="form-label">Context</label>
                    <textarea id="context" class="form-control" value="${description}" placeholder="${description}" required>${description}</textarea>
                    <div id="contextHelp" class="form-text">Enter the context of the post</div>
                </div>
                <div class="mb-3">
                    <label for="category" class="form-label">Category</label>
                    <select type="text" class="form-control" id="category" required>
                         <option value="1">Workshop</option>
                        <option value="2">Team Event</option>
                        <option value="3">Training</option>
                        <option value="4">Team Work</option>
                    </select>
                    <div id="categoryHelp" class="form-text">Choose post category</div>
                </div>
                <input type="file" class="form-control" id="customFile" accept="image/png, image/jpg, image/jpeg"/>
    
                <div id="btn-container" class="d-grid gap-2 d-md-block">
    
                    <button type="submit" class="btn btn-primary" id="submit">Submit</button>
                    <button type="submit" class="btn btn-warning" id="cancelButton" onclick="window.history.go(-1)">Cancel</button>
                </div>
    
            </form>`

        return HTML;
    }

    function displayTemplate(url) {
        const container = document.getElementById("addPostForm-section");
        fetchBlogPost(url)
            .then((post) => {
                const postHTML = generatePostHTML(post.title, post.authorName,
                    post.content);
                container.innerHTML = (postHTML);
            })
            .then(() => {

                document.getElementById("submit").addEventListener('click', async function (e) {
                    e.preventDefault();
                    await fetchUpdatePost.updatePost("api/posts");
                })
            });
    }

    return {
        displayTemplate: displayTemplate
    }

})();