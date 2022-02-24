export let deleteBlogPost = (function () {

    async function fetchDeleteBlogPost(url) {

        const response = await fetch(url, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
            body: null

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

    }

    function deletePost(url) {
        fetchDeleteBlogPost(url)
            .then(() => {
                alert("Deleted Successfully !");
                window.location.replace("/home-page");
            }).catch((e)=>{
                alert(e);
                window.history.back(-1);
            });
    }

    return {
        delete: deletePost
    }
})();