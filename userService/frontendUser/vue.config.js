module.exports = {
        devServer: {
            proxy: {
                '/api': {
                    target: ['http://localhost:8080','http://localhost:8081']
                }
            }
        }
};
