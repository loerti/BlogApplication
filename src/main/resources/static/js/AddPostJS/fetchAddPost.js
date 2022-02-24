export let fetchSavePost = (function () {

    function getFormData(id) {
        let title = document.getElementById("title").value;
        let content = document.getElementById("context").value;
        let image = document.getElementById("customFile").files[0].name;
        let category = document.getElementById("category").value;

        image = "images/HomePageImage/" + image;

        let data = {
            "blogId": id,
            "title": title,
            "content": content,
            "dateStamp": new Date(),
            "imagePath": image,
            "blogCategoryModel": {
                "categoryId": category
            }
        }
        return data;
    }

    async function fetchSaveBlogPost(url) {
        const data = getFormData(null);
        try {
            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json',
                    'Authorization': 'Bearer ' + 'JSESSIONID=451AA8E06CA0D1FFD44263E168A698A3'
                },
                body: JSON.stringify(data)
            });

            if (!response.ok){
                const data = await response.json();
                console.log(`Error Details:
                HTTP STATUS: ${data.httpStatus}
                Cause: ${data.message}`
                );

                throw new Error(data.message);
            }
            else
            return await response.json();
        } catch (e) {
            alert(e);
        }
    }

    function savePost(url) {
        fetchSaveBlogPost(url)
            .then((data) => {
                if(data!==undefined){
                    alert("Added Successfully !");
                    window.location.replace("/home-page");
                }
            })
    }

    return {
        save: savePost,
        getFormData: getFormData
    }


})();


