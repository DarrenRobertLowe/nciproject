async function search() {
    const options = {
        method: 'GET'
    };
    
    let response = fetch('/search?searchTerm='+document.getElementsByName("searchTerms")[0].value, options);
}