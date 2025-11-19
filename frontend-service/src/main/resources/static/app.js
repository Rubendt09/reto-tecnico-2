const BACKEND_URL = '/api/products';

async function loadProducts() {
    const loadingEl = document.getElementById('loading');
    const errorEl = document.getElementById('error');
    const productsContainer = document.getElementById('products-container');

    try {
        const response = await fetch(BACKEND_URL);
        
        if (!response.ok) {
            throw new Error(`Error HTTP: ${response.status}`);
        }

        const products = await response.json();
        
        loadingEl.style.display = 'none';
        
        if (products && products.length > 0) {
            productsContainer.innerHTML = products.map(product => `
                <div class="product-card">
                    <div class="product-id">ID: ${product.productId}</div>
                    <div class="product-title">${product.title}</div>
                </div>
            `).join('');
        } else {
            productsContainer.innerHTML = '<p>No se encontraron productos.</p>';
        }

    } catch (error) {
        console.error('Error al cargar productos:', error);
        loadingEl.style.display = 'none';
        errorEl.style.display = 'block';
        errorEl.textContent = `Error al conectar con el backend: ${error.message}`;
    }
}

document.addEventListener('DOMContentLoaded', () => {
    console.log('Frontend Service iniciado');
    console.log('Conectando a backend:', BACKEND_URL);
    loadProducts();
});

console.log('%c🚀 Reto Técnico 2 - Frontend Service', 'color: #667eea; font-size: 20px; font-weight: bold;');
console.log('%cKubernetes High Availability Architecture', 'color: #764ba2; font-size: 14px;');
