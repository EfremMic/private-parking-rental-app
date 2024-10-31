import React, { Component } from 'react';

class ErrorBoundary extends Component {
    constructor(props) {
        super(props);
        this.state = { hasError: false };
    }

    static getDerivedStateFromError(error) {
        // Update state so the next render will show the fallback UI
        return { hasError: true };
    }

    componentDidCatch(error, errorInfo) {
        // You could log the error to an error reporting service here
        console.error("Error captured in ErrorBoundary:", error, errorInfo);
    }

    render() {
        if (this.state.hasError) {
            // Render fallback UI
            return (
                <div>
                    <h2>Something went wrong.</h2>
                    <p>Please try again later or contact support if the issue persists.</p>
                </div>
            );
        }

        // Render children if there's no error
        return this.props.children;
    }
}

export default ErrorBoundary;
