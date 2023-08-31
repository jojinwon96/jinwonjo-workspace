module.exports = {
    devServer: {
        proxy: {
            '/api': {
                target: 'http://localhost:8087'
                // target: 'http://localhost:8085/seller/',
            },

        }
    }
}