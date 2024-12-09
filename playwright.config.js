const { defineConfig } = require('@playwright/test');

module.exports = defineConfig({
    reporter: [
        ['list'],
        ['allure-playwright'],
        ['json', { outputFile: 'test-results/results.json' }],
        ['html', { open: 'never' }],
    ],
    use: {
        headless: false,
        viewport: null,
        ignoreHTTPSErrors: true,
        video: 'retain-on-failure',
        screenshot: 'only-on-failure',
        baseURL: 'https://playwright.dev',
        trace: 'on-first-retry',
    },
    projects: [
        {
            name: 'chromium',
            use: {
                ...devices['Desktop Chrome'],
                launchOptions: {
                    args: ['--kiosk'],
                },
            },
        },
        {
            name: 'firefox',
            use: {
                ...devices['Desktop Firefox'],
                launchOptions: {
                    args: ['--kiosk'],
                },
            },
        },
        {
            name: 'webkit',
            use: {
                ...devices['Desktop Safari'],
                launchOptions: {
                    args: ['--kiosk'],
                },
            },
        },
    ],
    retries: 2,
    timeout: 60000,
    globalSetup: require.resolve('./global-setup'),
    globalTeardown: require.resolve('./global-teardown'),
});