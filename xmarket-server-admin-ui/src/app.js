import React from 'react';
import ReactDOM from 'react-dom';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import TestComponent from './component/TestComponent';

const App = () => (
    <MuiThemeProvider>
        <TestComponent />
    </MuiThemeProvider>
);

ReactDOM.render(
    <App />,
    document.getElementById('root')
);