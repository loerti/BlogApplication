import {fetchSavePost} from "../AddPostJS/fetchAddPost.js";

export let fetchUpdatePost = (function () {

    async function fetchUpdatePost(url) {
        const data = fetchSavePost.getFormData(postId);

        try {
            const response = await fetch(url, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json',
                    'Authorization': 'Bearer ' + 'JSESSIONID=451AA8E06CA0D1FFD44263E168A698A3'
                },
                body: JSON.stringify(data)
            });

            if(!response.ok){
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
        }
    }

    async function updatePost(url) {

        const response = await fetchUpdatePost(url);
        if (response != null) {
            alert("Updated Successfully !");
            window.location.replace("/home-page");
        }
    }

    return {
        updatePost: updatePost
    }

})();