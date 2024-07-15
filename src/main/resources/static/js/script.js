// Get the current theme from localStorage or default to light
let currentTheme = getTheme();
console.log(currentTheme);

document.addEventListener('DOMContentLoaded', () => {
    changeTheme(); // Initial call to apply the theme when the page loads
});

// Function to change the theme
function changeTheme() {
    // Apply the current theme to the page
    changePageTheme(currentTheme, currentTheme);

    // Set the listener to change theme button
    const changeThemeButton = document.querySelector('#theme_change_button');

    // Check if the button exists before adding the event listener
    if (changeThemeButton) {
        changeThemeButton.addEventListener("click", (event) => {
            console.log("change theme button clicked");

            const oldTheme = currentTheme;

            // Toggle the theme
            if (currentTheme === "dark") {
                currentTheme = "light";
            } else {
                currentTheme = "dark";
            }

            // Apply the new theme to the page
            changePageTheme(currentTheme, oldTheme);
        });
    } else {
        console.error("Theme change button not found!");
    }
}

// Set theme to localStorage
function setTheme(theme) {
    localStorage.setItem("theme", theme);
}

// Get theme from localStorage
function getTheme() {
    let theme = localStorage.getItem("theme");
    return theme ? theme : "light";
}

// Change the current page theme
function changePageTheme(theme, oldTheme) {
    // Update localStorage with the current theme
    setTheme(currentTheme);

    // Remove the old theme class and add the new theme class
    const htmlElement = document.querySelector("html");
    if (htmlElement) {
        htmlElement.classList.remove(oldTheme);
        htmlElement.classList.add(theme);
    } else {
        console.error("HTML element not found!");
    }

    // Update the button text to indicate the current theme
    const themeChangeButton = document.querySelector('#theme_change_button');
    if (themeChangeButton) {
        const spanElement = themeChangeButton.querySelector('span');
        if (spanElement) {
            spanElement.textContent = theme === "light" ? " Dark" : " Light";
        } else {
            console.error("Span element not found in theme change button!");
        }
    } else {
        console.error("Theme change button not found!");
    }
}
