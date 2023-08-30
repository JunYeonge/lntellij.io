// #frontend/src/App.jsx
import {Route, Routes} from 'react-router-dom'
import {BrowserRouter} from 'react-router-dom'
import Main from './pages/Main';
import  CreateBoard from './pages/CreateBoard';
import Detail from './pages/Detail';
import UpdateBoard from './pages/UpdateBoard';

function App(){
    return(
        <>
            <BrowserRouter>
                <Routes>
                    <Route path="/" element={<Main />} />
                    {/*경로 "/"에 대한 Main 컴포넌트를 매핑한다.*/}
                    <Route path="/detail" element={<Detail/>} />
                    <Route path="/update-board" element={<UpdateBoard />} />
                    <Route path="/create-board" element={<CreateBoard />} />
                </Routes>
            </BrowserRouter>
        </>
    )
}

export default App;