const ConvertData = (formData) => {
    const condensedObj = formData.map(item => ({ [item.field]: item.value })).reduce((acc, item) => Object.assign(acc, item), {});
    const newObj = new Object();
    Object.entries(condensedObj).forEach(([key, value]) => {
        if(key != "") {
            newObj[key] = value;
        }
    });
    console.log(newObj);
    return newObj;

}

export default ConvertData;
