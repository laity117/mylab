// 高德API Key
const apiKey = 'd5ce6f8c86dbb1236eca20c8a71684d8';
// 家乡的城市代码或名称，例如北京的adcode是110000
const cityCode = '卢龙县'; // 使用城市名称

async function fetchWeather() {
    try {
        const response = await fetch(`https://restapi.amap.com/v3/weather/weatherInfo?key=${apiKey}&city=${cityCode}`);
        if (!response.ok) throw new Error('Network response was not ok');
        const data = await response.json();

        // 假设data.lives[0]包含最新的天气信息
        const weatherInfo = data.lives[0];
        const weatherText = `当前天气: ${weatherInfo.weather} 气温: ${weatherInfo.temperature}°C`;

        document.getElementById('weather-info').textContent = weatherText;
    } catch (error) {
        console.error('Error fetching weather data:', error);
        document.getElementById('weather-info').textContent = '无法获取天气信息';
    }
}

// 页面加载完成后立即调用函数获取天气信息
window.addEventListener('load', fetchWeather);