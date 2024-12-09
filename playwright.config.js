// playwright.config.js
const { devices } = require('@playwright/test');

module.exports = {
    // Global settings
    use: {
        headless: false, // Set to true if you want to run tests in headless mode
        viewport: { width: 1280, height: 720 },
        ignoreHTTPSErrors: true,
        video: 'retain-on-failure', // Record video only when a test fails
        screenshot: 'only-on-failure', // Take screenshot only when a test fails
        baseURL: 'https://playwright.dev', // Base URL for your tests
        trace: 'on-first-retry', // Collect trace on first retry
    },

    // Configure projects for different browsers
    projects: [
        {
            name: 'chromium',
            use: { ...devices['Desktop Chrome'] },
        },
        {
            name: 'firefox',
            use: { ...devices['Desktop Firefox'] },
        },
        {
            name: 'webkit',
            use: { ...devices['Desktop Safari'] },
        },
        {
            name: 'Mobile Chrome',
            use: { ...devices['Pixel 5'] },
        },
        {
            name: 'Mobile Safari',
            use: { ...devices['iPhone 12'] },
        },
    ],

    // Configure retries
    retries: 2,

    // Configure reporters
    reporter: [
        ['list'], // Default reporter
        ['json', { outputFile: 'test-results/results.json' }], // JSON reporter
        ['html', { open: 'never' }], // HTML reporter
    ],

    // Configure test timeout
    timeout: 60000, // Set timeout for each test to 60 seconds

    // Configure global setup and teardown
    globalSetup: require.resolve('./global-setup'),
    globalTeardown: require.resolve('./global-teardown'),
};