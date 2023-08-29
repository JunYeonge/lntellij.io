// #frontend/src/App.jsx
import {Route, Routes} from 'react-router-dom'
import {BrowserRouter} from 'react-router-dom'
import Main from './pages/Main';
import  CreateBoard from './pages/CreateBoard';

function App(){
    return(
        <>
            <BrowserRouter>
                <Routes>
                    <Route path="/" element={<Main />} />
                    <Route path="/create-board" element={<CreateBoard />} />
                </Routes>
            </BrowserRouter>
        </>
    )
}

export default App;