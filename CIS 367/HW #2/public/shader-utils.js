/**
 * Created by Hans Dulimarta on 1/12/17.
 */
class ShaderUtils {
    static getShader(gl, code, type) {
        let shader = gl.createShader(type);
        gl.shaderSource(shader, code);
        gl.compileShader(shader);
        let error = gl.getShaderInfoLog(shader);
        if (error.length > 0) {
            let msg = (type === gl.VERTEX_SHADER ? "vertex" : "fragment") +
                " shader compile: " + error;
            throw msg;
        }
        return shader;
    }

    static loadFile(name) {
        return new Promise(function (resolve, reject) {
            let req = new XMLHttpRequest();
            let okStatus = document.location.protocol === "file:" ? 0 : 200;
            req.open("GET", name, true);
            req.onload = function () {
                if (req.status == okStatus) {
                    resolve(req.response);
                } else {
                    reject(Error(name + " " + req.statusText));
                }
            };

            req.onerror = function () {
                reject(Error("Network Error"));
            };
            req.send(null);
        });
    }

    static loadFromFile(gl, vshaderName, fShaderName) {
        let files = [{"name": vshaderName, "type": gl.VERTEX_SHADER},
            {"name": fShaderName, "type": gl.FRAGMENT_SHADER}]
            .map(obj => {
                return ShaderUtils.loadFile(obj.name)
                    .then(result => {
                        return {"text": result, "type": obj.type};
                    });
            });
        return Promise.all(files)
            .then(files => {
                let program = gl.createProgram();
                for (let f of files) {
                    let sh = ShaderUtils.getShader(gl, f.text, f.type);
                    gl.attachShader(program, sh);
                }
                gl.linkProgram(program);
                let error = gl.getProgramInfoLog(program);
                if (error.length > 0) {
                    let msg = "Shader program failed to link: " + error;
                    throw msg;
                }
                return program;
            })
            .catch(err => {
                ShaderUtils.showError(gl, err);
            });
    }

    static loadFromElement(gl, vertexId, fragmentId) {
        let vertElem = document.getElementById(vertexId);
        if (!vertElem) {
            alert("Unable to load vertex shader " + vertexId);
            return -1;
        }
        let vertShader = ShaderUtils.getShader(gl, vertElem.textContent, gl.VERTEX_SHADER);

        let fragElem = document.getElementById(fragmentId);
        if (!fragElem) {
            alert("Unable to load fragment shader " + fragmentId);
            return -1;
        }
        let fragShader = ShaderUtils.getShader(gl, fragElem.textContent, gl.FRAGMENT_SHADER);

        let program = gl.createProgram();
        gl.attachShader(program, vertShader);
        gl.attachShader(program, fragShader);
        gl.linkProgram(program);
        if (!gl.getProgramParameter(program, gl.LINK_STATUS)) {
            let msg = "Shader program failed to link. The error log is:"
                + "<pre>" + gl.getProgramInfoLog(program) + "</pre>";
            alert(msg);
            return -1;
        }
        return program;
    }

    static showError(gl, msg) {
        let container = gl.canvas.parentNode;
        container.innerHTML =
            "<div style='background: orangered; font-family: Courier;" +
            "color: white; " +
            "padding: 8px; " +
            "border-radius: 4px'>"
            + msg +
            "</div>";
    }
}
