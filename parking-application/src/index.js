import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter as Router } from 'react-router-dom';
import App from './App';
import ErrorBoundary from './components/ErrorBoundary'; // Import ErrorBoundary

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <Router>
        <ErrorBoundary>  {/* Wrap App in ErrorBoundary */}
            <App />
        </ErrorBoundary>
    </Router>
);
