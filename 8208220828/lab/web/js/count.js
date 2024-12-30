async function fetchConut() {
    try {
        const response = await fetch(`http://localhost:8080/lab_Web_exploded/count`);
        const data = await response.json();

        const countText = data.count;

        console.log(countText);

        document.getElementById('count').textContent = countText;
    } catch (error) {
        console.error('Error fetching count data:', error);
    }
}

// 页面加载完成后立即调用函数获取访问次数信息
window.addEventListener('load', fetchConut);